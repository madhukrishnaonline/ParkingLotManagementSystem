import { TicketModelContract } from './../../models/ticket-model';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ParkingSpotService } from '../../services/parking-spot.service';

@Component({
  selector: 'app-ticket-details',
  templateUrl: './ticket-details.component.html',
  styleUrls: ['./ticket-details.component.css']
})
export class TicketDetailsComponent implements OnInit {
  ticketIdFromParams!: string;
  ticketId!: string;
  ticket: TicketModelContract = {
    ticketId: '',
    spotId: 0,
    vehicleNo: '',
    vehicleType: '',
    entryTime: '',
    exitTime: '',
    price: 0,
    paid: false
  };

  constructor(private route: ActivatedRoute, private parkingService: ParkingSpotService) { }

  ngOnInit(): void {
    this.ticketIdFromParams = this.route.snapshot.paramMap.get('ticketId')!;
    if (this.ticketIdFromParams) {
      this.searchTicketDetails(this.ticketIdFromParams);
    } else {
      this.searchTicketDetails(this.ticketId);
    }
  }

  searchTicketDetails(ticketId: string) {
    this.parkingService.getTicketDetails(ticketId).subscribe(res => {
      this.ticket = res;
    },
      error => {
        console.error(error);
      })
  }
}