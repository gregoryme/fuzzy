## Temporal fuzzy logic project
#### _API of temporal fuzzy logic with Float values that is between [-1, +1]._

Actually, fuzzy temporal logic is a fascinating
approach to present data with variable and fuzzy truth.
I agree with is it the internal mechanism of fuzzy temporal logic is an issue
of interpretation. I've decided to build API on the following preconditions:
<br><br>

1. The Float is a base type of truth.
Truth values **v** can be between -1 and +1, i.e. **v** ∈ [-1, 1]. We round values for two decimal places.


2. Operation NOT defined as:
   _<center>not a ≡ -a, where a ∈ [-1, 1]</center>_


3. Operation AND defined as:
   _<center>a AND b ≡ if (a < 0 || b < 0) then => -|a • b| else => |a • b|</center>_
   _<center>where a, b ∈ [-1, 1]</center>_


4. Operation OR defined as:
   _<center>a OR b ≡ if (a != 0 && b != 0) then => Max (a, b) else => a + b</center>_
   _<center>where a, b ∈ [-1, 1]</center>_

   
5. We define a specific method - ***trigger*** -
that serves as a bridge between fuzzy value and traditional
boolean value. The trigger uses a lambda function to determine
the threshold value when a fuzzy value can be interpreted as true.


6. We define a specific class-factory: ***TemporalFuzzyVarFactory***
is a "time machine" that can produce a "fuzzy boolean" object in state
depends on time. The factory has a specific lambda "time function" that defines
rules to produce objects with different states. Time function expects parameter of
LocalDateTime which can be interpreted as definite time.


### _The API is at the stage of development and testing._