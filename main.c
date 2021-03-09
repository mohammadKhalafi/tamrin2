#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <math.h>







int main()
{
    int a = 12;
    void *ptr = &a;
    printf("%d", *ptr);
    return 0;
}











/*
char * reverse (char * string);
int main (){
    int n;
    int size;
    char temp[1000];
    scanf("%d", &n);
    char ** strings = (char **)calloc(n, sizeof(char *));
    for (int i = 0; i < n; i++){
        scanf("%s", temp);
        size = strlen(temp);
        *(strings + i) = (char *)calloc(size + 1, 1);
        strcpy(*(strings + i), temp);
    }
    int min = 200;
    int minimom;
    for(int i = 0; i < n; i++)
        if (min > strlen(*(strings + i)) ){
            min = strlen(*(strings + i));
            minimom = i;
        }

    int number_of_zir_donbale = min * (min - 1) / 2 + min;
    char ** zir_donbale = (char **)calloc(number_of_zir_donbale, sizeof(char *));
    int J = 0;
    for (int i = 1; i <= min; i++){
        for (int j = 0; j <= min - i; j++){
            *(zir_donbale + J) = (char *)calloc(i + 1, 1);
            strncpy(*(zir_donbale + J), (*(strings + minimom)) + j, i);
            J++;
        }
    }
    int check = 1;
    for(int i = number_of_zir_donbale - 1; i >= 0; i--){
        check = 1;
        for(int j = 0; j < n; j++){
            if ( strstr ( strings[j], *(zir_donbale + i)) == NULL && strstr(strings[j], reverse( zir_donbale[i] )) == NULL){
                check = 0;
            }
        }
        if(check == 1){
            if (strstr(strings[0], *(zir_donbale + i)) == NULL)
            {
                printf("%s", reverse(*(zir_donbale + i)) );
            }
            printf("%s", *(zir_donbale + i));
            return 0;
        }

    }

}




char * reverse (char * string){
    char temp;
    int size = strlen(string);
    char * ans = (char *)calloc(size + 1, 1);
    for (int i = 0; i < size; i++){
        ans[i] = string[size - 1 - i];
    }
    return ans;
}



*/










/*


int main(){
    int a;
    int b;
    scanf("%d%d", &a, &b);
    if (a >= 0 && b >= 0)
    printf("%d\n", zarb(a, b));
    else if (a < 0 && b < 0)
        printf("%d", zarb(-a, -b));
    else if (a < 0 && b > 0)
        printf("%d", -zarb(-a , b));
    else
        printf("%d", -zarb(a , -b));

}

int zarb(int a, int b){
    if (b == 0)
        return 0;
    if (b == 1)
        return a;
    return sum(zarb(a , b - 1) , a);
}

int sum(int a, int b){
    if (b == 0)
        return a;
    return sum(a, b - 1) + 1;

}















*/









/*
int main (){
    char string[1000] = {'\0'};
    gets(string);
    reverse(string);
    printf("%s", string);
}
void reverse (char * string){
    int size = strlen(string);
    for (int i = 0; i < size/2; i++){
        swap(&string[i], &string[size - 1 - i]);
    }
}
void swap(char * ch1, char * ch2){
    char temp = *ch1;
    *ch1 = *ch2;
    *ch2 = temp;
    return;
}

*/






















/*
int search (char *, char);
int main (){
    char string[1000];
    scanf("%[^\n]s", string);
    char ch;

    scanf("%*c%c", &ch);
    printf("%d", search(string, ch));
}
int search (char * string, char ch){
    for(int i = 0; i < strlen(string); i++){
        if(string[i] == ch){
            return i;
        }
    }
    return -1;
}
*/





















/*
int main(){
    char * string1 = (char *) calloc(100, 1);
    char * string2 = (char *) calloc(100, sizeof(char));
    gets(string1);
    gets(string2);
    printf("%d", str(string1, string2));
}
int str(char * s1, char * s2){
    int size = max(strlen(s1), strlen(s2));
    for(int i = 0; i < size; i++){
        if (s1[i] != s2[i])
            return i;
    }
    return -1;
}
int max (int a, int b){
    if(a >= b)
        return a;
    return b;
    }
*/

















/*
int main(){
    printf("%d", fibonachi(9));
}
int fibonachi(int n){
    if(n == 1)
        return 1;
    if(n == 2)
        return 1;
    return fibonachi(n - 1) + fibonachi(n - 2);
}
*/















/*
void m (void (*)(int));

void array(int a){
    printf("hoho %d\n", a);
}
void array2(int a){
    printf("%d", a + 99);
}
int main(){
    int (*f) (int a);
    f = array;
    (*f) (5);

    f = array2;
    (*f)(1);
    m(array);
    m(array2);

}
void m(void (*function)(int a)){
    (*function)(5);
}

*/

















/*
char character (int * hihi);
int size;
int main()
{
    int n, m, r;
    scanf("%d%d%d", &n, &m, &r);
    int *** matrix  = (int ***)calloc(n,sizeof(int **));

    for(int i = 0; i < n; i++)
        * (matrix + i) = (int **) calloc(m, sizeof(int *));


    for (int i = 0; i < n; i++){
        for(int j = 0; j < m; j++) {
            (*((*(matrix + i)) + j)) =(int *) calloc(r + 1, sizeof(int));
        }
    }
    char boolean;
    char temp;
    fflush(stdin);
    for(int i = 0; i < n; i++){
        for(int j = 0; j < m; j++){
            fflush(stdin);
            scanf(" %c", &boolean);

            if(boolean == 'T'){
                *((*((*(matrix + i)) + j))) = 1;
                for(int t = 1; t <= r; t++){
                    scanf("%d", ((*((*(matrix + i)) + j)) + t));
                }
            }
            else if(boolean == 'F'){
                *((*((*(matrix + i)) + j))) = 0;
                for(int t = 1; t <= r; t++){
                    scanf(" %c", &temp);
                    *((*((*(matrix + i)) + j)) + t) = temp;
                }
            }
            else
                 *((*((*(matrix + i)) + j))) = 2;
        }
    }


    int ** ans_array = (int **)calloc(n , sizeof(int *));
    for(int i = 0; i < n; i++)
        *(ans_array + i) = (int *)calloc(m, sizeof(int));

    size = r;
    int ans;

    for(int i = 0; i < n; i++){
        for(int j = 0; j < m; j++){
            if(*((*((*(matrix + i)) + j))) == 1)
                ans = number( ((*((*(matrix + i)) + j))) );

            else if(*((*((*(matrix + i)) + j))) == 0)
                ans = character( ((*((*(matrix + i)) + j))) );

            *(*(ans_array + i) + j) = ans;
        }
    }

    char ch;
    for(int i = 0; i < n; i++){
        for(int j = 0; j < m; j++){
            if(*((*((*(matrix + i)) + j))) == 1)
                printf("%d ", *(*(ans_array + i) + j) );
            else if(*((*((*(matrix + i)) + j))) == 0){
                ch = *(*(ans_array + i) + j);
                printf("%c ", ch);
            }

        }
        printf("\n");
    }

}
int number (int * hehe){
    int Size = size;

    int * hoho = (int *)calloc(Size + 1, sizeof(int));
    int i = 1;
    int j = 1;
    while(1){
        if(i > Size){
            break;
        }
        if((*(hehe + i)) == 0){
            i++;
            continue;
        }
        *( hoho + j) = (*(hehe + i));
        while( (*(hehe + i)) * (*(hehe + i + 1)) > 0){
            if(i + 1 > Size)
                break;
            *(hoho + j) += *(hehe + i + 1);
            i++;
        }
        i++;
        j++;

    }
    j--;
    int ans = 0;
    int temp;

    for(int ii  = 1; ii <= j ; ii++){

        for(int iii = ii; iii <= j; iii++){
            temp = 0;
            for(int iiii = 0; iiii <= iii - ii; iiii++){

                temp += *(hoho + ii + iiii);
            }
            if(ans < temp){
                ans = temp;
            }
        }
    }
    free(hoho);
    return ans;
}

char character (int * hihi){
    char ans = 0;
    char temp;
    for(int i = 1; i <= size; i++){
        temp = tolower(*(hihi + i));
        if(ans < temp)
            ans = temp;
    }
    return ans;
}
*/
