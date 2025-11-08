import { Component, OnInit } from '@angular/core';
import { Conference, TypeConference } from '../../models/conference.model';
import { Keynote } from '../../models/keynote.model';
import { ConferenceService } from '../../services/conference.service';
import { KeynoteService } from '../../services/keynote.service';

@Component({
  selector: 'app-conferences',
  templateUrl: './conferences.component.html',
  styleUrls: ['./conferences.component.css']
})
export class ConferencesComponent implements OnInit {
  conferences: Conference[] = [];
  keynotes: Keynote[] = [];
  selectedConference: Conference | null = null;
  isEditing: boolean = false;
  newConference: Conference = this.initConference();
  typeConferenceOptions = Object.values(TypeConference);

  constructor(
    private conferenceService: ConferenceService,
    private keynoteService: KeynoteService
  ) { }

  ngOnInit(): void {
    this.loadConferences();
    this.loadKeynotes();
  }

  loadConferences(): void {
    this.conferenceService.getAllConferences().subscribe({
      next: (data) => this.conferences = data,
      error: (error) => console.error('Erreur lors du chargement des conférences', error)
    });
  }

  loadKeynotes(): void {
    this.keynoteService.getAllKeynotes().subscribe({
      next: (data) => this.keynotes = data,
      error: (error) => console.error('Erreur lors du chargement des keynotes', error)
    });
  }

  initConference(): Conference {
    return {
      titre: '',
      type: TypeConference.ACADEMIQUE,
      date: '',
      duree: 60,
      nombreInscrits: 0,
      score: 0,
      keynoteId: 0
    };
  }

  selectConference(conference: Conference): void {
    this.selectedConference = { ...conference };
    this.isEditing = true;
  }

  saveConference(): void {
    if (this.isEditing && this.selectedConference && this.selectedConference.id) {
      this.conferenceService.updateConference(this.selectedConference.id, this.selectedConference).subscribe({
        next: () => {
          this.loadConferences();
          this.cancelEdit();
        },
        error: (error) => console.error('Erreur lors de la mise à jour', error)
      });
    } else {
      this.conferenceService.createConference(this.newConference).subscribe({
        next: () => {
          this.loadConferences();
          this.newConference = this.initConference();
        },
        error: (error) => console.error('Erreur lors de la création', error)
      });
    }
  }

  deleteConference(id: number | undefined): void {
    if (id && confirm('Êtes-vous sûr de vouloir supprimer cette conférence ?')) {
      this.conferenceService.deleteConference(id).subscribe({
        next: () => this.loadConferences(),
        error: (error) => console.error('Erreur lors de la suppression', error)
      });
    }
  }

  cancelEdit(): void {
    this.selectedConference = null;
    this.isEditing = false;
  }

  getKeynoteName(keynoteId: number): string {
    const keynote = this.keynotes.find(k => k.id === keynoteId);
    return keynote ? `${keynote.prenom} ${keynote.nom}` : 'N/A';
  }
}
