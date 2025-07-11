import { RouterModule, Routes } from '@angular/router';
import { NgModule } from "@angular/core";
import { HomePageComponent } from '../components/home-page/home-page.component';
import { ParkingSpotComponent } from '../components/parking-spot/parking-spot.component';
import { DashboardComponent } from '../components/dashboard/dashboard.component';
import { InvoiceComponent } from '../components/invoice/invoice.component';
import { SpotHistoryComponent } from '../components/spot-history/spot-history.component';
import { TicketHistoryComponent } from '../components/ticket-history/ticket-history.component';
import { ParkVehicleComponent } from '../components/park-vehicle/park-vehicle.component';
import { ExitVehicleComponent } from '../components/exit-vehicle/exit-vehicle.component';
import { PaymentComponent } from '../components/payment/payment.component';

const routes: Routes = [
    { path: 'home', component: HomePageComponent },
    { path: '', redirectTo: '/home', pathMatch: 'full' },
    { path: 'dashboard', component: DashboardComponent },
    { path: 'park', component: ParkVehicleComponent },
    { path: 'exit', component: ExitVehicleComponent },
    { path: 'pay', component: PaymentComponent },
    { path: 'invoice/:ticketId', component: InvoiceComponent },
    // { path: 'status', component: SpotStatusComponent },
    { path: 'history', component: TicketHistoryComponent },
    { path: 'spot-history/:spotId', component: SpotHistoryComponent }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class RoutingModule { }