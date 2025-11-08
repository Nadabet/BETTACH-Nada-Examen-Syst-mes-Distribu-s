import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { KeynotesComponent } from './components/keynotes/keynotes.component';
import { ConferencesComponent } from './components/conferences/conferences.component';
import { ReviewsComponent } from './components/reviews/reviews.component';

const routes: Routes = [
  { path: '', redirectTo: '/conferences', pathMatch: 'full' },
  { path: 'keynotes', component: KeynotesComponent },
  { path: 'conferences', component: ConferencesComponent },
  { path: 'reviews/:id', component: ReviewsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
