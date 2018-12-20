import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AccountsListComponent } from './component/accounts-list/accounts-list.component';
import { DispositionComponent } from './component/disposition/disposition.component';

const routes: Routes = [
  {
    path: 'accounts',
    component: AccountsListComponent
  },
  {
    path: 'dispositions',
    component: DispositionComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
