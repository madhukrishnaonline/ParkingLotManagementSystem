import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

const parkingSpotUrl: string = '';
@Injectable({
    providedIn: 'root'
})
export class ParkingSpotService {
    getHistory(vehicleNo: string): Observable<any> {
        throw new Error('Method not implemented.');
    }
    downloadInvoicePdf(ticketId: string): Observable<Blob> {
        return this.http.get(`http://localhost:8080/invoice/pdf/${ticketId}`, {
            responseType: 'blob'
        });
    }

    getRecentTickets(): Observable<any> {
        throw new Error('Method not implemented.');
    }
    getDashboardStats(): Observable<any> {
        throw new Error('Method not implemented.');
    }
    getInvoice(ticketId: string): Observable<any> {
        throw new Error('Method not implemented.');
    }
    constructor(private http: HttpClient) { };

    confirmPayment(ticketId: any): Observable<any> {
        throw new Error('Method not implemented.');
    }


    exitVehicle(ticketId: any): Observable<any> {
        return this.http.get<any>(parkingSpotUrl);
    }

    parkVehicle(vehicle: { vehicleNo: any; type: any; }, strategy: any): Observable<any> {
        return this.http.post(parkingSpotUrl, vehicle);
    }

    public getAvailableSpots(): Observable<any[]> {
        return this.http.get<any[]>(`http://localhost:7979/spots/available`);
    }

    public getAllSpotStatus(): Observable<any[]> {
        return this.http.get<any[]>(`${parkingSpotUrl}status`);
    }

    public getSpotHistory(spotId: number): Observable<any[]> {
        return this.http.get<any[]>(`${parkingSpotUrl}history/${spotId}`);
    }
}