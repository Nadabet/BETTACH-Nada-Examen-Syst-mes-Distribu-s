import { Component, OnInit } from '@angular/core';
import { Keynote } from '../../models/keynote.model';
import { KeynoteService } from '../../services/keynote.service';

@Component({
  selector: 'app-keynotes',
  templateUrl: './keynotes.component.html',
  styleUrls: ['./keynotes.component.css']
})
export class KeynotesComponent implements OnInit {
  keynotes: Keynote[] = [];
  selectedKeynote: Keynote | null = null;
  isEditing: boolean = false;
  newKeynote: Keynote = this.initKeynote();

  constructor(private keynoteService: KeynoteService) { }

  ngOnInit(): void {
    this.loadKeynotes();
  }

  loadKeynotes(): void {
    this.keynoteService.getAllKeynotes().subscribe({
      next: (data) => this.keynotes = data,
      error: (error) => console.error('Erreur lors du chargement des keynotes', error)
    });
  }

  initKeynote(): Keynote {
    return {
      nom: '',
      prenom: '',
      email: '',
      fonction: ''
    };
  }

  selectKeynote(keynote: Keynote): void {
    this.selectedKeynote = { ...keynote };
    this.isEditing = true;
  }

  saveKeynote(): void {
    if (this.isEditing && this.selectedKeynote && this.selectedKeynote.id) {
      this.keynoteService.updateKeynote(this.selectedKeynote.id, this.selectedKeynote).subscribe({
        next: () => {
          this.loadKeynotes();
          this.cancelEdit();
        },
        error: (error) => console.error('Erreur lors de la mise à jour', error)
      });
    } else {
      this.keynoteService.createKeynote(this.newKeynote).subscribe({
        next: () => {
          this.loadKeynotes();
          this.newKeynote = this.initKeynote();
        },
        error: (error) => console.error('Erreur lors de la création', error)
      });
    }
  }

  deleteKeynote(id: number | undefined): void {
    if (id && confirm('Êtes-vous sûr de vouloir supprimer ce keynote ?')) {
      this.keynoteService.deleteKeynote(id).subscribe({
        next: () => this.loadKeynotes(),
        error: (error) => console.error('Erreur lors de la suppression', error)
      });
    }
  }

  cancelEdit(): void {
    this.selectedKeynote = null;
    this.isEditing = false;
  }
}
