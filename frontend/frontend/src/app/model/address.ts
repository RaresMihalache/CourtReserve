import { Court } from "./court";

export class Address {
    id?: number;
    street?: string;
    number?: number;
    city?: string;
    courts?: Court[];
}