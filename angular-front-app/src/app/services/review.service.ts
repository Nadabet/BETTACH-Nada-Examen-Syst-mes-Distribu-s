import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Review } from '../models/review.model';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private apiUrl = 'http://localhost:9999/api/conferences';

  constructor(private http: HttpClient) { }

  getReviewsByConference(conferenceId: number): Observable<Review[]> {
    return this.http.get<Review[]>(`${this.apiUrl}/${conferenceId}/reviews`);
  }

  createReview(conferenceId: number, review: Review): Observable<Review> {
    return this.http.post<Review>(`${this.apiUrl}/${conferenceId}/reviews`, review);
  }

  deleteReview(conferenceId: number, reviewId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${conferenceId}/reviews/${reviewId}`);
  }
}
