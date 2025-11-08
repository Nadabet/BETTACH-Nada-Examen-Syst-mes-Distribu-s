import { Keynote } from './keynote.model';

export enum TypeConference {
  ACADEMIQUE = 'ACADEMIQUE',
  COMMERCIALE = 'COMMERCIALE'
}

export interface Conference {
  id?: number;
  titre: string;
  type: TypeConference;
  date: string;
  duree: number;
  nombreInscrits: number;
  score: number;
  keynoteId: number;
  keynote?: Keynote;
}
