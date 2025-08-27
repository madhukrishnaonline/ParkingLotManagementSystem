import { Component, OnInit } from '@angular/core';
import { ParkingSpotService } from '../../services/parking-spot.service';
import { VehicleModelContract } from '../../models/vehicle-model';
import { TicketModelContract } from '../../models/ticket-model';
// <!-- 1. Install chart library -->
// npm install chart.js ng2-charts
// import { ChartConfiguration, ChartOptions } from 'chart.js';
// import { Color, Label } from 'ng2-charts';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  /*revenueChartData: ChartConfiguration<'line'>['data'] = {
    labels: [],
    datasets: [
      {
        data: [],
        label: 'Revenue by Day',
        fill: true,
        borderColor: '#007bff',
        backgroundColor: 'rgba(0,123,255,0.2)',
        tension: 0.4
      }
    ]
  };*/

  stats: any = {};
  constructor(private parkingService: ParkingSpotService) { }

  ngOnInit(): void {
    this.getRecentParkedVehicles();
    this.getRecentExitedVehicles();
    this.getAvailableSpots();
    this.getOccupiedSpots();
    this.getVehiclesCount();
    this.getTotalRevenue();
    // this.parkingService.getRevenueTrend().subscribe(data => {
    //   this.revenueChartData.labels = data.map(d => d.date);
    //   this.revenueChartData.datasets[0].data = data.map(d => d.amount);
    // });
  }

  availableSpots: any[] = [];
  getAvailableSpots() {
    this.parkingService.getAvailableSpots().subscribe({
      next: (response) => {
        this.availableSpots = response
        // console.log(this.availableSpots);
      }, error: (errorResponse) => console.log(errorResponse)
    });
  }

  occupiedSpots: any[] = [];
  getOccupiedSpots() {
    this.parkingService.getOccupiedSpots().subscribe({
      next: (response) => this.occupiedSpots = response,
      error: (errorResponse) => console.log(errorResponse),
    });
  }

  vehiclesCount: number = 0;
  getVehiclesCount() {
    this.parkingService.getVehiclesCount().subscribe({
      next: (count) => this.vehiclesCount = count,
      error: (errorResponse) => console.log(errorResponse)
    });
  }

  recentParkedVehicles: TicketModelContract[] = [];
  getRecentParkedVehicles() {
    this.parkingService.getRecentParkedVehicles().subscribe({
      next: (response) => this.recentParkedVehicles = response,
      error: (errorResponse) => console.log(errorResponse)
    });
  }

  recentExitedVehicles: TicketModelContract[] = [];
  getRecentExitedVehicles() {
    this.parkingService.getRecentExitedVehicles().subscribe({
      next: (response) => {
        this.recentExitedVehicles = response;
      },
      error: (errorResponse) => console.log(errorResponse)
    });
  }

  totalRevenue: number = 0;
  getTotalRevenue() {
    this.parkingService.getTotalRevenue().subscribe({
      next: (revenue) => this.totalRevenue = revenue,
      error: (errorResponse) => console.log(errorResponse)
    });
  }
}