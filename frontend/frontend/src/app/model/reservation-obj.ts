import { Court } from "./court";

export class Reservation {
    id?: string;
    subscriptionId?: string;
    startTime?: string;
    endTime?: string;
    status?: string;
    court?: Court;
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
    users?: [{
        id?: string;
        name?: string;
    },
    {
        id?: string;
        name?: string;
    }]

}