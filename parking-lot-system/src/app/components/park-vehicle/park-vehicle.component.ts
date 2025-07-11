import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ParkingSpotService } from '../../services/parking-spot.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-park-vehicle',
  templateUrl: './park-vehicle.component.html',
  styleUrls: ['./park-vehicle.component.css']
})
export class ParkVehicleComponent {
  parkForm: FormGroup;
  strategies: string[] = ['defaultStrategy', 'nearestStrategy', 'randomStrategy'];

  constructor(
    private fb: FormBuilder,
    private parkingService: ParkingSpotService,
    private toastr: ToastrService
  ) {
    this.parkForm = this.fb.group({
      vehicleNo: ['', Validators.required],
      type: ['TWO_WHEELER', Validators.required],
      strategy: ['defaultStrategy']
    });
  }

  park() {
    if (this.parkForm.invalid) return;
    const { vehicleNo, type, strategy } = this.parkForm.value;

    this.parkingService.parkVehicle({ vehicleNo, type }, strategy).subscribe({
      next: (ticket) => {
        this.toastr.success('Vehicle Parked. Ticket ID: ' + ticket.id);
        this.parkForm.reset({ type: 'TWO_WHEELER', strategy: 'defaultStrategy' });
      },
      error: () => {
        this.toastr.error('Failed to park the vehicle.');
      }
    });
  }
}