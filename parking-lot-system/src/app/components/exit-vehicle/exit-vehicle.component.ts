import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { ParkingSpotService } from '../../services/parking-spot.service';
import { BillResponseContract } from '../../models/bill-response.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-exit-vehicle',
  templateUrl: './exit-vehicle.component.html',
  styleUrls: ['./exit-vehicle.component.css']
})
export class ExitVehicleComponent {
  exitForm: FormGroup;
  showConfirm: boolean = false;
  // billAmount: number | null = null;
  billResponse: BillResponseContract = {
    ticketId: '',
    vehicleNo: '',
    vehicleType: '',
    entryTime: '',
    exitTime: '',
    totalMinutes: '',
    price: 0,
    message: ''
  }

  constructor(
    private fb: FormBuilder,
    private parkingService: ParkingSpotService,
    private toastr: ToastrService,
    private router: Router
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
        this.billResponse = amount;
        this.toastr.success('Vehicle exited. Bill: ₹' + JSON.stringify(amount));
        this.exitForm.reset();
        this.showConfirm = false;
        this.router.navigateByUrl('/pay');
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
