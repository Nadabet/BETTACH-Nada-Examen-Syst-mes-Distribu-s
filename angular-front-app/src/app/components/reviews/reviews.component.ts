import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Review } from '../../models/review.model';
import { Conference } from '../../models/conference.model';
import { ReviewService } from '../../services/review.service';
import { ConferenceService } from '../../services/conference.service';

@Component({
  selector: 'app-reviews',
  templateUrl: './reviews.component.html',
  styleUrls: ['./reviews.component.css']
})
export class ReviewsComponent implements OnInit {
  conferenceId: number = 0;
  conference: Conference | null = null;
  reviews: Review[] = [];
  newReview: Review = this.initReview();

  constructor(
    private route: ActivatedRoute,
    private reviewService: ReviewService,
    private conferenceService: ConferenceService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.conferenceId = +params['id'];
      this.loadConference();
      this.loadReviews();
    });
  }

  loadConference(): void {
    this.conferenceService.getConferenceById(this.conferenceId).subscribe({
      next: (data) => this.conference = data,
      error: (error) => console.error('Erreur lors du chargement de la conférence', error)
    });
  }

  loadReviews(): void {
    this.reviewService.getReviewsByConference(this.conferenceId).subscribe({
      next: (data) => this.reviews = data,
      error: (error) => console.error('Erreur lors du chargement des reviews', error)
    });
  }

  initReview(): Review {
    return {
      texte: '',
      note: 5
    };
  }

  addReview(): void {
    this.reviewService.createReview(this.conferenceId, this.newReview).subscribe({
      next: () => {
        this.loadReviews();
        this.loadConference();
        this.newReview = this.initReview();
      },
      error: (error) => console.error('Erreur lors de l\'ajout du review', error)
    });
  }

  deleteReview(reviewId: number | undefined): void {
    if (reviewId && confirm('Êtes-vous sûr de vouloir supprimer ce review ?')) {
      this.reviewService.deleteReview(this.conferenceId, reviewId).subscribe({
        next: () => {
          this.loadReviews();
          this.loadConference();
        },
        error: (error) => console.error('Erreur lors de la suppression', error)
      });
    }
  }

  getStars(note: number): string {
    return '⭐'.repeat(note);
  }
}
