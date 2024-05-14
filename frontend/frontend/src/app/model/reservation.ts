import { Court } from "./court";
import { User } from "./user";

export class ReservationRequest {
    subscriptionId?: string;
    reservation?: {
        startTime?: string;
        endTime?: string;
        court?: {
            id?: string;
            number?: string;
            address?: {
                id?: string;
                street?: string;
                number?: string;
                city?: string;
            }
        }
        users?: [{
            id?: string;
            name?: string;
        }];
    };
    sendNotificationTo?: User;
}