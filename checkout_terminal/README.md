### Description 

Consider a store where each item has a amount per unit, and may also have a volume amount. 
For example, apples may be $1.00 each or 4 for $3.00.

Implement a point-of-sale scanning API that accepts an arbitrary ordering of products 
(similar to what would happen at a checkout line) and then returns the correct total
amount for an entire shopping cart based on the per unit prices or the volume prices as applicable.

Here are the products listed by code and the prices to use (there is no sales tax):

|Product Code | Prices                     |
|------------:|:---------------------------|
|A            | $2.00 each or 4 for $7.00  |
|B            | $12.00                     |
|C            | $1.25 or $6 for a six pack |
|D            | $0.15                      |

For your solution, we ask that you use Groovy.

There should be a top level point of sale terminal service object or namespace that looks something like the pseudo-code below.
You are free to design and implement the rest of the code however you wish, including how you specify the prices in the system:

```
terminal.setPricing(...)
terminal.scan("A")
terminal.scan("C")
... etc.
result = terminal.total
```

Here are the minimal inputs you should use for your test cases. These test cases must be shown to work in your program:

- Scan these items in this order: ABCDABAA; Verify the total amount is $32.40.
- Scan these items in this order: CCCCCCC; Verify the total amount is $7.25.
- Scan these items in this order: ABCD; Verify the total amount is $15.40.

## Extension 

For example, the type of item we already handle works like this:
A G costs 1.00 each, but they're available in cases of 10 for 9.00 a case, so:

| Quantity             | Total |
|---------------------:|:------|
| G                    | 1.00  |
| GGG                  | 3.00  |
| GGGGGGGGGG (10 Gs)   | 9.00  |
| GGGGGGGGGGGG (12 Gs) | 11.00 |

Whereas the new type of item we want to handle should work like this:
An F costs 1.00 each, but only 0.90 each when buying 10 or more, so:

| Scanned items        | Total                     |
|---------------------:|:--------------------------|
| F                    | 1.00  <-- Same as G       |
| FFF                  | 3.00  <-- Same as G       |
| FFFFFFFFFF (10 Fs)   | 9.00  <-- Different from G|
| FFFFFFFFFFFF (12 Fs) | 10.80 <-- Different from G|
