import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';

import { AppComponent } from './app.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { HeaderPageComponent } from './components/header-page/header-page.component';
import { FooterPageComponent } from './components/footer-page/footer-page.component';
import { ParkingSpotComponent } from './components/parking-spot/parking-spot.component';
import { RoutingModule } from './routes/app-routing.module';
import { TicketHistoryComponent } from './components/ticket-history/ticket-history.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ExitVehicleComponent } from './components/exit-vehicle/exit-vehicle.component';
import { ParkVehicleComponent } from './components/park-vehicle/park-vehicle.component';
import { PaymentComponent } from './components/payment/payment.component';
import { LoginComponent } from './components/login/login.component';
import { AuthInterceptor } from './services/auth-interceptor.service';
import { TicketDetailsComponent } from './components/ticket-details/ticket-details.component';

@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    HeaderPageComponent,
    FooterPageComponent,
    ParkingSpotComponent,
    TicketHistoryComponent,
    DashboardComponent,
    ExitVehicleComponent,
    ParkVehicleComponent,
    PaymentComponent,
    LoginComponent,
    TicketDetailsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule,
    RoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({
      positionClass: 'toast-top-right',
      preventDuplicates: true
    })
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }