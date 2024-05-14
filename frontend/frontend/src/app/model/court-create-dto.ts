import { Address } from "./address";
import { Tariff } from "./tariff";

export class CourtCreateDto {
    id?: string;
    number?: string;
    status?: string;
    surface?: string;
    address?: Address;
    tariff?: Tariff;
}