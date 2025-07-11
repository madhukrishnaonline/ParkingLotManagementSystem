import { Component, OnInit } from '@angular/core';
import { ParkingSpotService } from '../../services/parking-spot.service';

@Component({
  selector: 'app-ticket-history',
  templateUrl: './ticket-history.component.html',
  styleUrls: ['./ticket-history.component.css']
})
export class TicketHistoryComponent implements OnInit {
  history: any[] = [];
  vehicleNo: string = '';

  constructor(private parkingService: ParkingSpotService) {}

  ngOnInit() {
    this.search();
  }

  search() {
    this.parkingService.getHistory(this.vehicleNo).subscribe(res => {
      this.history = res;
    });
  }
}