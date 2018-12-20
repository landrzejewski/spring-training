import {environment} from '../environments/environment';

export class Api {
  accounts = `${environment.baseUrl}/accounts`;
  dispositions = `${environment.baseUrl}/dispositions`;
}
