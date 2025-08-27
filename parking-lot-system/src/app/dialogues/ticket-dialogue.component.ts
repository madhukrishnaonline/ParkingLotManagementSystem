import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
    selector: 'app-ticket-id-dialog',
    template: `
    <h2 mat-dialog-title>Enter Ticket ID</h2>
    <mat-dialog-content>
      <mat-form-field appearance="outline">
        <mat-label>Ticket ID</mat-label>
        <input matInput [(ngModel)]="ticketId" placeholder="Enter your ticket id">
      </mat-form-field>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button (click)="dialogRef.close()">Cancel</button>
      <button mat-raised-button color="primary" (click)="submit()">Submit</button>
    </mat-dialog-actions>
  `,
})
export class TicketIdDialogComponent {
    ticketId: string = '';

    constructor(public dialogRef: MatDialogRef<TicketIdDialogComponent>) { }

    submit() {
        this.dialogRef.close(this.ticketId);
    }
}