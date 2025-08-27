import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { ParkingSpotService } from '../../services/parking-spot.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header-page',
  templateUrl: './header-page.component.html',
  styleUrls: ['./header-page.component.css']
})
export class HeaderPageComponent {

  ticketId: string = '';
  constructor(
    private parkingService: ParkingSpotService,
    private router: Router,
    private toastr: ToastrService) { }

  // Submit ticket and navigate
  submitTicket() {
    if (!this.ticketId) {
      this.toastr.error('Please enter a ticket ID');
      return;
    }

    this.parkingService.getTicketDetails(this.ticketId).subscribe({
      next: (ticket) => {
        this.toastr.success('Ticket found. Redirecting...');
        const modalElement = document.getElementById('ticketModal');

        // Navigate to payment page with ticket ID
        this.router.navigate(['/pay-bill', this.ticketId]);
      },
      error: () => {
        this.toastr.error('Ticket not found or expired');
      }
    });
  }

}