import { User } from "./user";

export class SubscriptionResponse {
    id?: string;
    reservations?: [{
        id?: string;
        startTime?: string;
        endTime?: string;
        users?: User[];
    }]
}