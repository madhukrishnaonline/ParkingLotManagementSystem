export interface VehicleModelContract {
    ticketId: string;
    spotId: number;
    vehicleNo: string;
    vehicleType: string;
    entryTime: string;
    exitTime: string;
    price: number;
    paid: boolean;
}