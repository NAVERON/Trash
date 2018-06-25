

#include <stdio.h>
#include <stdlib.h>
#include "testheader.h"


int foo(int a, int b)  
{  
  printf("you input %d and %d\n", a, b);  
  return a+b;  
}

double get_double(int a, int b, double c)
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







