

#include <stdio.h>
#include <stdlib.h>


int foo(int a, int b)  
{
  printf("you input %d and %d\n", a, b);  
  return a+b;  
}

int get_double(int a, int b, int c)
{
	int acount = a + b;
	if(acount > c)
	{
		return acount;
	}
	else
	{
		return c;
	}
}







