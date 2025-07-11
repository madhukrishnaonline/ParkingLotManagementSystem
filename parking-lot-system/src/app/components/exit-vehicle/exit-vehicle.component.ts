import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { ParkingSpotService } from '../../services/parking-spot.service';

@Component({
  selector: 'app-exit-vehicle',
  templateUrl: './exit-vehicle.component.html',
  styleUrls: ['./exit-vehicle.component.css']
})
export class ExitVehicleComponent {
 exitForm: FormGroup;
  showConfirm: boolean = false;
  billAmount: number | null = null;

  constructor(
    private fb: FormBuilder,
    private parkingService: ParkingSpotService,
    private toastr: ToastrService
  ) {
    this.exitForm = this.fb.group({
      ticketId: ['', Validators.required]
    });
  }

  confirmExit() {
    this.showConfirm = true;
  }

  proceedExit() {
    const { ticketId } = this.exitForm.value;
    this.parkingService.exitVehicle(ticketId).subscribe({
      next: (amount) => {
        this.billAmount = amount;
        this.toastr.success('Vehicle exited. Bill: ₹' + amount);
        this.exitForm.reset();
        this.showConfirm = false;
      },
      error: () => {
        this.toastr.error('Ticket not found or already exited.');
        this.showConfirm = false;
      }
    });
  }

  cancelExit() {
    this.showConfirm = false;
  }
}
