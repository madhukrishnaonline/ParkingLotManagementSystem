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
import { LoginComponent } from '../components/login/login.component';
import { AppRouteSecurityGuard } from './app-route.guard';
import { TicketDetailsComponent } from '../components/ticket-details/ticket-details.component';

const routes: Routes = [
    { path: 'home', component: HomePageComponent },
    { path: '', redirectTo: '/home', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
    { path: 'status', component: ParkingSpotComponent },
    { path: 'dashboard', component: DashboardComponent, canActivate: [AppRouteSecurityGuard] },
    { path: 'park', component: ParkVehicleComponent, canActivate: [AppRouteSecurityGuard] },
    { path: 'exit', component: ExitVehicleComponent, canActivate: [AppRouteSecurityGuard] },
    { path: 'pay', component: PaymentComponent, canActivate: [AppRouteSecurityGuard] },
    { path: 'ticket', component: TicketDetailsComponent, canActivate: [AppRouteSecurityGuard] },
    { path: 'invoice/:ticketId', component: InvoiceComponent, canActivate: [AppRouteSecurityGuard] },
    // { path: 'status', component: SpotStatusComponent },
    { path: 'history', component: TicketHistoryComponent, canActivate: [AppRouteSecurityGuard] },
    { path: 'spot-history/:spotId', component: SpotHistoryComponent, canActivate: [AppRouteSecurityGuard] }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class RoutingModule { }