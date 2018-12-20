import { Component } from '@angular/core';
import { AccountService } from '../../service/account.service';
import { ResultPage } from '../../model/result-page.model';

@Component({
  selector: 'app-accounts-list',
  templateUrl: './accounts-list.component.html',
  styleUrls: ['./accounts-list.component.css']
})
export class AccountsListComponent {
  resultPage: ResultPage<Account>;
  constructor(private accountService: AccountService) {
    this.reload(0);
  }
  private reload(pageNumber) {
    this.accountService.getAccounts(pageNumber)
      .subscribe(resultPage => this.resultPage = resultPage);
  }
  createAccount() {
    this.accountService.createAccount()
      .subscribe(account => this.reload(this.resultPage.pageNumber));
  }
  previous() {
    this.reload(this.resultPage.pageNumber - 1);
  }
  next() {
    this.reload(this.resultPage.pageNumber + 1);
  }
}
