class Fire:
    """
    k is show how many hours that spend from time the fire make
    x and y : show fire position
    """

    def __init__(self, k, x, y):
        self.x = x
        self.y = y
        self.k = k


def expond_pora(quee_person, map, target, n, m):

    depth = quee_person[-1][2]

    while quee_person != [] and quee_person[-1][2] == depth:

        pora = quee_person.pop()

        if map[pora[0]][pora[1]] != 'f':

            if 0 <= pora[0] < n and 0 <= pora[1] + 1 < m and map[pora[0]][pora[1] + 1] != 'f' and map[pora[0]][pora[1] + 1] != 's':

                if pora[0] == target[0] and pora[1] + 1 == target[1]:

                    quee_person = [(pora[0], pora[1] + 1, depth + 1)] + quee_person
                    return [True, quee_person]
                else:

                    map[pora[0]][pora[1] + 1] = 's'
                    quee_person = [(pora[0], pora[1] + 1, depth + 1)] + quee_person

            if 0 <= pora[0] < n and 0 <= pora[1] - 1 < m and map[pora[0]][pora[1] - 1] != 'f' and map[pora[0]][pora[1] - 1] != 's':

                if pora[0] == target[0] and pora[1] - 1 == target[1]:

                    quee_person = [(pora[0], pora[1] - 1, depth + 1)] + quee_person
                    return [True, quee_person]
                else:

                    map[pora[0]][pora[1] - 1] = 's'
                    quee_person = [(pora[0], pora[1] - 1, depth + 1)] + quee_person

            if 0 <= pora[0] - 1 < n and 0 <= pora[1] < m and map[pora[0] - 1][pora[1]] != 'f' and map[pora[0] - 1][pora[1]] != 's':

                if pora[0] - 1 == target[0] and pora[1] == target[1]:

                    quee_person = [(pora[0] - 1, pora[1], depth + 1)] + quee_person
                    return [True, quee_person]
                else:

                    map[pora[0] - 1][pora[1]] = 's'
                    quee_person = [(pora[0] - 1, pora[1], depth + 1)] + quee_person

            if 0 <= pora[0] + 1 < n and 0 <= pora[1] < m and map[pora[0] + 1][pora[1]] != 'f' and map[pora[0] + 1][pora[1]] != 's':

                if pora[0] + 1 == target[0] and pora[1] == target[1]:

                    quee_person = [(pora[0] + 1, pora[1], depth + 1)] + quee_person
                    return [True, quee_person]
                else:
                    map[pora[0] + 1][pora[1]] = 's'
                    quee_person = [(pora[0] + 1, pora[1], depth + 1)] + quee_person

    return [False, quee_person]


def update_fire(quee_fire, map, n, m, hour):
    size_of_quee = len(quee_fire)
    item_see = 0
    number_place_fire_new = 0
    while quee_fire != [] and item_see < size_of_quee:

        if quee_fire[-1].k + 1 == hour:

            fire = quee_fire.pop()

            if 0 <= fire.x + 1 < n and 0 <= fire.y + 1 < m and map[fire.x + 1][fire.y + 1] != 'f':
                number_place_fire_new += 1

                quee_fire = [Fire(0, fire.x + 1, fire.y + 1)] + quee_fire

                map[fire.x + 1][fire.y + 1] = 'f'
                #map[fire.x + 1] = map[fire.x + 1][:fire.y + 1] + "f" + map[fire.x + 1][fire.y + 2:]

            if 0 <= fire.x + 1 < n and 0 <= fire.y - 1 < m and map[fire.x + 1][fire.y - 1] != 'f':
                number_place_fire_new += 1

                quee_fire = [Fire(0, fire.x + 1, fire.y - 1)] + quee_fire

                map[fire.x + 1][fire.y - 1] = 'f'
                #map[fire.x + 1] = map[fire.x + 1][:fire.y - 1] + "f" + map[fire.x + 1][fire.y:]

            if 0 <= fire.x + 1 < n and 0 <= fire.y < m and map[fire.x + 1][fire.y] != 'f':
                number_place_fire_new += 1

                quee_fire = [Fire(0, fire.x + 1, fire.y)] + quee_fire

                map[fire.x + 1][fire.y] = 'f'
                #map[fire.x + 1] = map[fire.x + 1][:fire.y] + "f" + map[fire.x + 1][fire.y + 1:]

            if 0 <= fire.x - 1 < n and 0 <= fire.y + 1 < m and map[fire.x - 1][fire.y + 1] != 'f':
                number_place_fire_new += 1

                quee_fire = [Fire(0, fire.x - 1, fire.y + 1)] + quee_fire

                map[fire.x - 1][fire.y + 1] = 'f'
                #map[fire.x - 1] = map[fire.x - 1][:fire.y + 1] + "f" + map[fire.x - 1][fire.y + 2:]

            if 0 <= fire.x - 1 < n and 0 <= fire.y - 1 < m and map[fire.x - 1][fire.y - 1] != 'f':
                number_place_fire_new += 1

                quee_fire = [Fire(0, fire.x - 1, fire.y - 1)] + quee_fire

                map[fire.x - 1][fire.y - 1] = 'f'
                #map[fire.x - 1] = map[fire.x - 1][:fire.y - 1] + "f" + map[fire.x - 1][fire.y:]

            if 0 <= fire.x - 1 < n and 0 <= fire.y < m and map[fire.x - 1][fire.y] != 'f':
                number_place_fire_new += 1

                quee_fire = [Fire(0, fire.x - 1, fire.y)] + quee_fire

                map[fire.x - 1][fire.y] = 'f'
                #map[fire.x - 1] = map[fire.x - 1][:fire.y] + "f" + map[fire.x - 1][fire.y + 1:]

            if 0 <= fire.x < n and 0 <= fire.y + 1 < m and map[fire.x][fire.y + 1] != 'f':
                number_place_fire_new += 1

                quee_fire = [Fire(0, fire.x, fire.y + 1)] + quee_fire

                map[fire.x][fire.y + 1] = 'f'
                #map[fire.x] = map[fire.x][:fire.y + 1] + "f" + map[fire.x][fire.y + 2:]

            if 0 <= fire.x < n and 0 <= fire.y - 1 < m and map[fire.x][fire.y - 1] != 'f':
                number_place_fire_new += 1

                quee_fire = [Fire(0, fire.x, fire.y - 1)] + quee_fire

                map[fire.x][fire.y - 1] = 'f'
                #map[fire.x] = map[fire.x][:fire.y - 1] + "f" + map[fire.x][fire.y:]

        else:

            fire = quee_fire.pop()
            fire.k += 1
            quee_fire = [fire] + quee_fire

        item_see += 1
        # just update node that be in quee fire before we call this function

    return [number_place_fire_new, quee_fire]


def main():
    temp = run()
    while temp != -2:
        if temp == -1:
            print("Impossible")
            # if there is NO way to airplane

        else:
            print(temp)
            # print number smallest time to find to the airplane

        temp = run()


def print_plan(plan):

    for k in plan:

        print(''.join(k))


def run():
    n, m, hour = list(map(int, input().split()))
    # m and n is table size
    # hour speed of fire expand

    if m == 0 and n == 0 and hour == 0:
        return -2
        # exit from program if three number Enter is 0,0,0 with number -2

    plan = []

    quee_fire = []

    quee_person = []

    x_T = 0
    y_T = 0
    x = 0
    y = 0
    number_of_empty_place = 0

    """
    with this code we add the position of each fire and Fire and person position 
    and find out target .
    """
    for i in range(n):
        input_string = input()
        plan.append(list(input_string))
        y = 0
        for k in input_string:
            if k == 'f':
                quee_fire = [Fire(0, x, y)] + quee_fire
            if k == 't':
                x_T = x
                y_T = y
                target = (x_T, y_T)
            if k == 's':

                quee_person = [(x, y, 0)] + quee_person

            else:
                number_of_empty_place += 1

            y += 1

        x += 1

    find_goal = False
    # Shows us that we find target our not

    number_of_depth_of_bfs = 0
    # Shows the number of our steps

    while number_of_empty_place >= 0:
        # for time fire doesn't full our plan and we try to find a path

        # print_plan(plan)
        # print("\n\n\n")
        number_of_depth_of_bfs += 1
        # with each time this while be implement our person have one action and have one steps to find target

        # # update poras or person
        #
        # if find_goal:
        #     # print_plan(plan)
        #     #print("2")
        #     # print_plan(plan)
        #     return number_of_depth_of_bfs

        find_goal, quee_person = expond_pora(quee_person, plan, target, n, m)
        z, quee_fire = update_fire(quee_fire, plan, n, m, hour)
        # update fire in plan

        number_of_empty_place -= z
        # update the empty place that fire can't full it

        if plan[target[0]][target[1]] == 'f' or len(quee_person) == 0:
        # if plan[target[0]][target[1]] == 'f' or len(quee_person) == 0:

            # if all of person in plan die or target place full with fire we can't take to the target
            # print("1")
            # print_plan(plan)

            return -1

        # if find_goal:
        #     # print_plan(plan)
        #     #print("2")
        #     # print_plan(plan)
        #     return number_of_depth_of_bfs
        # number_of_depth_of_bfs += 1
        # # with each time this while be implement our person have one action and have one steps to find target
        #
        # find_goal, quee_person = expond_pora(quee_person, plan, target, n, m)
        # # update poras or person
        #
        if find_goal:
        #     print("2")
        #    print_plan(plan)
           return number_of_depth_of_bfs

    # print("3")
    # print_plan(plan)
    return -1


if __name__ == '__main__':
    main()
