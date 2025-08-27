export interface TicketModelContract {
    ticketId: string;
    spotId: number;
    vehicleNo: string;
    vehicleType: string;
    entryTime: string;
    exitTime: string;
    totalTimeVehicleParked?: number;
    price: number;
    paid: boolean;
}