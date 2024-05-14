import { Address } from "./address";
import { Tariff } from "./tariff";

export class Court {
    id!: string;
    number!: string;
    surface!: string;
    status!: string;
    address!: Address;
    tariff?: Tariff;
}