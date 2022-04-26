## Temporal fuzzy logic project
#### _API of temporal fuzzy logic with Float values that is between [-1, +1] and Double values for time argument_

### Part One. Fuzzy logic

#### NOT fuzzy operation

_not a ≡ -a, where a ∈ [-1, 1]_

#### AND fuzzy operation

_a AND b ≡ if (a < 0 || b < 0) then => -|a • b| else => |a • b|_

#### OR fuzzy operation

_a OR b ≡ if (a != 0 && b != 0) then => Max (a, b) else => a + b_