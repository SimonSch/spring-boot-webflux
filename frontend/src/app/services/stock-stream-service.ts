import {Stock} from "../entities/stock-entity";
import {Injectable, NgZone} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable()
export class StockStreamService {

  private readonly _stock = 'http://localhost:8080/api/stocks';
  private stocks:Stock[] = [];

  constructor(private http:HttpClient, private _ngZone: NgZone){
  }

  getAllStocksStream(): Observable<Stock[]>{
    return Observable.create((observer) => {
      this.stocks = [];
      const eventSource = new EventSource(`${this._stock}`);
      eventSource.onmessage = (event) => {
        console.log('The stream has been opened');
        this.stocks.push(JSON.parse(event.data));
        this._ngZone.run(()  => observer.next(this.stocks));
      };
      eventSource.onerror = (error) => {
        if (eventSource.readyState === 0){
          console.log('The stream has been closed by the server');
          eventSource.close();
          observer.complete();
        }else{
          observer.error('Event source error: ' + error);
        }
      };
    })
  }


}
