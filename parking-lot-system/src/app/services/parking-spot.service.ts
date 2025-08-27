import { TicketModelContract } from './../models/ticket-model';
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { VehicleModelContract } from '../models/vehicle-model';
import { BillResponseContract } from '../models/bill-response.model';

declare const parkingSpotUrl: string;
declare const entryGateUrl: string;
declare const exitGateUrl: string;
declare const ticketHistoryUrl: string;
declare const vehicleURL: string;
@Injectable({
    providedIn: 'root'
})
export class ParkingSpotService {
    constructor(private http: HttpClient) { };
    
    public getVehiclesCount(): Observable<number> {
        return this.http.get<number>(`${vehicleURL}count`);
    }

    getTicketDetails(ticketId: string): Observable<TicketModelContract> {
        return this.http.get<TicketModelContract>(`${ticketHistoryUrl}history/${ticketId}`);
    }

    getVehicleHistory(vehicleNo: string): Observable<VehicleModelContract[]> {
        return this.http.get<VehicleModelContract[]>(`${vehicleURL}history/${vehicleNo}`);
    }
    
    downloadInvoicePdf(ticketId: string): Observable<Blob> {
        return this.http.get(`${ticketHistoryUrl}invoice/${ticketId}`, {
            responseType: 'blob'
        });
    }
    getRecentParkedVehicles(): Observable<TicketModelContract[]> {
        return this.http.get<TicketModelContract[]>(`${vehicleURL}parkedVehicles`);
    }
    
    getRecentExitedVehicles():Observable<TicketModelContract[]> {
        return this.http.get<TicketModelContract[]>(`${vehicleURL}exitedVehicles`);
    }

    getTotalRevenue():Observable<number>{
        return this.http.get<number>(`${parkingSpotUrl}totalRevenue`);
    }

    getDashboardStats(): Observable<any> {
        throw new Error('Method not implemented.');
    }
    getInvoice(ticketId: string): Observable<any> {
        throw new Error('Method not implemented.');
    }

    confirmPayment(ticketId: any): Observable<any> {
        return this.http.post(`${exitGateUrl}pay/${ticketId}`, null);
    }

    exitVehicle(ticketId: string): Observable<BillResponseContract> {
        return this.http.post<BillResponseContract>(`${exitGateUrl}out/${ticketId}`, "");
    }

    parkVehicle(vehicle: { vehicleNo: any; type: any; }, strategy: any): Observable<TicketModelContract> {
        return this.http.post<TicketModelContract>(`${entryGateUrl}park?strategy=${strategy}`, vehicle);
    }

    public getAvailableSpots(): Observable<any[]> {
        return this.http.get<any[]>(`${parkingSpotUrl}available`);
    }

    public getOccupiedSpots(): Observable<any[]> {
        return this.http.get<any[]>(`${parkingSpotUrl}occupied`);
    }

    public getAllSpotStatus(): Observable<any[]> {
        return this.http.get<any[]>(`${parkingSpotUrl}status`);
    }

    public getSpotHistory(spotId: number): Observable<any[]> {
        return this.http.get<any[]>(`${parkingSpotUrl}history/${spotId}`);
    }
}