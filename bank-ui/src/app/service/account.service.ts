import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ResultPage } from '../model/result-page.model';
import { Disposition } from '../model/disposition.model';
import { Observable } from 'rxjs';
import {Api} from '../api';

@Injectable()
export class AccountService {
  constructor(private httpClient: HttpClient, private api: Api) {
  }
  getAccounts(pageNumber = 0, pageSize = 10): Observable<ResultPage<Account>> {
    const params = {pageNumber: `${pageNumber}`, pageSize: `${pageSize}`};
    return this.httpClient.get<ResultPage<Account>>(this.api.accounts, {params});
  }
  createAccount(): Observable<Account> {
    return this.httpClient.post<Account>(this.api.accounts, {});
  }
  processDisposition(disposition: Disposition) {
    return this.httpClient.post<void>(this.api.dispositions, disposition);
  }
}
