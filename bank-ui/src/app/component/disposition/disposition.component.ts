import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Disposition } from '../../model/disposition.model';
import { AccountService } from '../../service/account.service';

@Component({
  selector: 'app-disposition',
  templateUrl: './disposition.component.html',
  styleUrls: ['./disposition.component.css']
})
export class DispositionComponent {
  disposition = new Disposition();
  constructor(private accountService: AccountService, private router: Router) {
  }
  showAccounts() {
    this.router.navigateByUrl('accounts');
  }
  confirm() {
    this.accountService.processDisposition(this.disposition)
      .subscribe(() => this.showAccounts());
  }
}
