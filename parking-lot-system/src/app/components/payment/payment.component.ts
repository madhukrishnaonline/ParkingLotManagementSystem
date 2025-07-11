import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ParkingSpotService } from '../../services/parking-spot.service';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent {
  payForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private parkingService: ParkingSpotService,
    private toastr: ToastrService,
    private router: Router
  ) {
    this.payForm = this.fb.group({
      ticketId: ['', Validators.required]
    });
  }

  payNow() {
    const { ticketId } = this.payForm.value;
    this.parkingService.confirmPayment(ticketId).subscribe({
      next: () => {
        this.toastr.success('Payment confirmed');
        this.router.navigate(['/invoice', ticketId]);
      },
      error: () => {
        this.toastr.error('Payment failed or ticket not found');
      }
    });
  }
}
