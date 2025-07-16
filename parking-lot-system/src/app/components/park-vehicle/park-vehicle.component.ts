import { TicketModelContract } from './../../models/ticket-model';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ParkingSpotService } from '../../services/parking-spot.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-park-vehicle',
  templateUrl: './park-vehicle.component.html',
  styleUrls: ['./park-vehicle.component.css']
})
export class ParkVehicleComponent {
  parkForm: FormGroup;
  strategies: string[] = ['defaultEntrance', 'nearToEntrance', 'nearToExit'];

  constructor(
    private fb: FormBuilder,
    private parkingService: ParkingSpotService,
    private toastr: ToastrService,
    private router: Router
  ) {
    this.parkForm = this.fb.group({
      vehicleNo: ['', Validators.required],
      type: ['TWO_WHEELER', Validators.required],
      strategy: ['defaultEntrance']
    });
  }

  ticketDetails: TicketModelContract = {
    ticketId: '',
    spotId: 0,
    vehicleNo: '',
    vehicleType: '',
    entryTime: '',
    exitTime: '',
    price: 0,
    paid: false
  }

  parkVehicle() {
    if (this.parkForm.invalid) return;
    const { vehicleNo, type, strategy } = this.parkForm.value;
    this.parkingService.parkVehicle({ vehicleNo, type }, strategy).subscribe({
      next: (ticket) => {
        this.ticketDetails = ticket;
        this.toastr.success('Vehicle Parked. Ticket ID: ' + this.ticketDetails.ticketId);

        // ✅ Reset form
        this.parkForm.reset({ type: 'TWO_WHEELER', strategy: 'defaultEntrance' });

        // ✅ Redirect to ticket details page
        this.router.navigate(['/ticket', this.ticketDetails.ticketId]);
      },
      error: () => {
        this.toastr.error('Failed to park vehicle.');
      }
    });
  }
}