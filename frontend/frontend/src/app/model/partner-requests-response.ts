import { User } from "./user";

export class PartnerRequestsResponse {
    id?: string;
    startTime?: string;
    endTime?: string;
    users?: User[];
}