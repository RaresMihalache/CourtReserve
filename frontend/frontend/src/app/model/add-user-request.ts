import { User } from '../model/user'

export class AddUserRequest {
    id!: string;
    user!: User;
}