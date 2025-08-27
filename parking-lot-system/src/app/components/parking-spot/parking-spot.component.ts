import { Component, OnInit } from '@angular/core';
import { ParkingSpotService } from '../../services/parking-spot.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-parking-spot',
  templateUrl: './parking-spot.component.html',
  styleUrls: ['./parking-spot.component.css']
})
export class ParkingSpotComponent implements OnInit {
  entryForm: FormGroup;
  ticket: any;
  errorMsg: string = '';

  constructor(private fb: FormBuilder, private parkingService: ParkingSpotService) {
    this.entryForm = this.fb.group({
      vehicleNo: ['', [Validators.required, Validators.pattern(/^[A-Z]{2}\s?\d{4}$/)]],
      vehicleType: ['TWO_WHEELER', Validators.required],
      strategyName: ['defaultStrategy'],
    });
  }

  submit() {
    if (this.entryForm.invalid) return;

    const vehicle = {
      vehicleNo: this.entryForm.value.vehicleNo,
      type: this.entryForm.value.vehicleType,
    };

    const strategy = this.entryForm.value.strategyName;

    this.parkingService.parkVehicle(vehicle, strategy).subscribe({
      next: (res) => {
        this.ticket = res;
        this.errorMsg = '';
        this.entryForm.reset({ vehicleType: 'TWO_WHEELER', strategyName: 'defaultStrategy' });
      },
      error: (err) => {
        this.ticket = null;
        this.errorMsg = err.error?.message || 'Something went wrong';
      },
    });
  }

  parkingSpots: any[] = [];

  //constructor(private parkingService: ParkingSpotService) { }

  public getAvailableSpots() {
    this.parkingService.getAvailableSpots().subscribe({
      next: (spots) => this.parkingSpots = spots,
      error: (errorResponse) => console.log(errorResponse)
    });
  }

  ngOnInit(): void {
    this.getAvailableSpots();
  }

}