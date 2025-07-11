import { Component, OnInit } from '@angular/core';
import { ParkingSpotService } from '../../services/parking-spot.service';
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
  recentTickets: any[] = [];

  constructor(private parkingService: ParkingSpotService) { }

  ngOnInit(): void {
    this.parkingService.getDashboardStats().subscribe((res) => (this.stats = res));
    this.parkingService.getRecentTickets().subscribe((res) => (this.recentTickets = res));

    // this.parkingService.getRevenueTrend().subscribe(data => {
    //   this.revenueChartData.labels = data.map(d => d.date);
    //   this.revenueChartData.datasets[0].data = data.map(d => d.amount);
    // });
  }
}