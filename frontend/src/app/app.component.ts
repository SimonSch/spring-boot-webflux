import {Component, OnInit, ViewChild} from '@angular/core';
import {StockStreamService} from "./services/stock-stream-service";
import {Stock} from "./entities/stock-entity";
import {AgGridNg2} from "ag-grid-angular";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'live-updates-app';

  @ViewChild('table') agGrid: AgGridNg2;
  columnDefs = [
    {headerName: 'id', field: 'id'},
    {headerName: 'Name', field: 'name'},
    {headerName: 'Price', field: 'price'}
  ];

  rowData: any;

  constructor(private stockService: StockStreamService) {
  }

  ngOnInit() {
    this.stockService.getAllStocksStream().subscribe( (data) => {
      console.log('new data');
      this.agGrid.api.setRowData(data);
    });
  }
}
