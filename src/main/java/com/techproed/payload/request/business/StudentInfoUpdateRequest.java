package com.techproed.payload.request.business;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false) // Prevents inheriting annotations like @NotNull
public class StudentInfoUpdateRequest extends StudentInfoRequest {
}
