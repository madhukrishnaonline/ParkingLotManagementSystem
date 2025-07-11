import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ParkingSpotService } from '../../services/parking-spot.service';

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrls: ['./invoice.component.css']
})
export class InvoiceComponent implements OnInit {
  ticketId!: string;
  invoiceData: any;

  constructor(
    private route: ActivatedRoute,
    private parkingService: ParkingSpotService,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.ticketId = this.route.snapshot.paramMap.get('ticketId') || '';
    this.parkingService.getInvoice(this.ticketId).subscribe({
      next: (data) => (this.invoiceData = data),
      error: () => this.toastr.error('Unable to load invoice')
    });
  }

  downloadInvoice() {
    const content = JSON.stringify(this.invoiceData, null, 2);
    const blob = new Blob([content], { type: 'application/json' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `invoice-${this.ticketId}.json`;
    a.click();
    window.URL.revokeObjectURL(url);
  }

  downloadPDF() {
    const ticketId = this.ticketId;
    this.parkingService.downloadInvoicePdf(ticketId).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `invoice-${ticketId}.pdf`;
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: () => this.toastr.error('Failed to download PDF.')
    });
  }
}
