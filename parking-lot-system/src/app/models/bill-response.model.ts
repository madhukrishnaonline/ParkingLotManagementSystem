export interface BillResponseContract {
    ticketId: string;
    vehicleNo: string;
    vehicleType: string;
    entryTime: string;
    exitTime: string;
    totalMinutes: string;
    price: number;
    message: string;
}