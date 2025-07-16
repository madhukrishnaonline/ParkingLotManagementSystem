import { Component, OnInit } from '@angular/core';
import { ParkingSpotService } from '../../services/parking-spot.service';
import { VehicleModelContract } from '../../models/vehicle-model';
import { TicketModelContract } from '../../models/ticket-model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ticket-history',
  templateUrl: './ticket-history.component.html',
  styleUrls: ['./ticket-history.component.css']
})
export class TicketHistoryComponent implements OnInit {
  histories: VehicleModelContract[] = [];
  vehicleNo: string = '';

  constructor(private parkingService: ParkingSpotService, private router: Router) { }

  ngOnInit() {
    this.search();
  }

  search() {
    this.parkingService.getVehicleHistory(this.vehicleNo).subscribe(res => {
      this.histories = res;
    });
  }
}