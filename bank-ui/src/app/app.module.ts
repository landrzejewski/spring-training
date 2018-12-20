import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AccountService } from './service/account.service';
import { AccountsListComponent } from './component/accounts-list/accounts-list.component';
import { DispositionComponent } from './component/disposition/disposition.component';
import { Api } from './api';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    AccountsListComponent,
    DispositionComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    AccountService,
    Api
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
