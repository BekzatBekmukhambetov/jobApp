package com.jobapp.service.impl;

import com.jobapp.domain.Company;
import com.jobapp.domain.Review;
import com.jobapp.repository.ReviewRepository;
import com.jobapp.service.CompanyService;
import com.jobapp.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private CompanyService companyService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews;
    }

    @Override
    public boolean addReview(Long companyId, Review review) {
        Company company = companyService.getCompanyById(companyId);
        if(company!=null){
            review.setCompany(company);
            reviewRepository.save(review);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Review getReview(Long companyId, Long reviewId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);

        return reviews.stream().filter(review -> review.getId().equals(reviewId)).findFirst().orElse(null);

    }

    @Override
    public boolean updateReview(Long companyId, Long reviewId, Review updatedreview) {
        if(companyService.getCompanyById(companyId)!= null){
            updatedreview.setCompany(companyService.getCompanyById(companyId));
            updatedreview.setId(reviewId);
            reviewRepository.save(updatedreview);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean deleteReview(Long companyId, Long reviewId) {
        if(companyService.getCompanyById(companyId)!= null &&
        reviewRepository.existsById(reviewId)){
            Review review = reviewRepository.findById(reviewId).orElse(null);
            Company company  = review.getCompany();
            company.getReviews().remove(review);
            review.setCompany(null);
            companyService.updateCompany(companyId, company);
            reviewRepository.deleteById(reviewId);
            return true;
        }else {
            return false;
        }
    }
}
