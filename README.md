# jwp-subway-path

## 구간 등록 기능

- 지하철 노선에 구간을 등록하는 기능을 구현
- 새로운 구간의 상행역 또는 하행역은 기존 노선에 존재하는 역과 하나만 일치해야한다.
- 기존 노선에 등록된 역이 없다면 새로운 구간과 일치하는 역이 하나도 없어도 등록 가능하다
- 새로운 구간 등록시 위 조건에 부합하지 않는 경우 에러 처리한다.

## 구간 제거 기능

- 지하철 노선에 구간을 제거하는 기능 구현
- 위치에 상관 없이 삭제가 가능하다.
- 종점이 제거될 경우 다음으로 오던 역이 종점이 된다.
- 지하철 노선에 상행 종점역과 하행 종점역만 있는 경우(구간이 1개인 경우) 역을 삭제할 수 없다.
- 중간역이 제거될 경우 재배치 한다.
    - 노선에 A - B - C 역이 연결되어 있을 때 B역을 제거할 경우 A - C로 재배치 된다.
    - 거리는 두 구간의 거리의 합으로 정한다.
- 새로운 구간 제거시 위 조건에 부합하지 않는 경우 에러 처리한다.

## 구간 관리 기능의 예외 케이스를 고려하기

- 구간 등록과 제거 기능의 예외케이스들에 대한 시나리오를 정의
- 인수 테스트를 작성하고 이를 만족시키는 기능을 구현

## 구간 삭제 기능의 예외 케이스를 고려하기

- 노선에 등록되어있지 않은 역을 제거하려 한다.

## 클래스 설명

- Line : 지하철 노선
    - [field] : 아이디, 이름, 색
- Station : 지하철 역
    - [field] : 아이디, 이름
- Section : 역과 역 사이를 잇는 구간
    - [field] : 아이디, 구간의 상행역(upStation), 구간의 하행역(downStation), 노선, 역과 역사이의 거리
    - [method] divideDownSection : 기존 구간과 새로 추가되는 구간 차이의 아래 구간을 반환
    - [method] divideUpSection : 기존 구간과 새로 추가되는 구간 차이의 위 구간을 반환
    - [method] findDuplicatedSection : 기존 구간과 새로 추가하는 구간이 겹치면 기존 구간 반환
    - [method] linkToDown : 상행 구간(upSection)의 상행역과 하행 구간(downSection)의 하행역을 연결한 구간 반환
- Sections
    - [field] : 구간의 리스트를 관리
    - [method] findUpSectionFrom : 입력 받은 역(Station)을 기준으로 상행 구간(upSection)을 반환 (상행 구간의 하행역 = 입력받은 역)
    - [method] findDownSectionFrom : 입력 받은 역(Station)을 기준으로 하행 구간(downSection)을 반환 (하행 구간의 상행역 = 입력받은 역)
    - [method] sortStations : 구간에 포함된 모든역을 상행 종점역(upTerminusStation) -> 하행 종점역(downTerminusStation) 순으로 리턴
    - [method] findModifiedSection : 기존 구간과 새로 추가되는 구간의 겹치는 부분을 제외한 구간을 리턴
- Distance (Value Object) : 역 사이의 거리
  - [field] : 거리

----
## 1단계 - 기능

- [x] 구간 등록 기능
    - [x] 조건 1. 기존 하행 종점역 == 새로운 상행역
    - [x] 조건 2. 새로운 하행역 != 기존 노선에 등록되어 있는 역
    - [x] 조건 3. 조건에 부합하지 않는 경우 에러 처리
- [x] 구간 제거 기능
    - [x] 조건 1. 기존 하행 종점역 "만" 제거 가능
    - [x] 조건 2. 구간이 1인 경우 제거 불가능
    - [x] 조건 3. 조건에 부합하지 않는 경우 에러 처리

----

## 2단계 - 기능

- [x] 기존 구간의 역을 기준으로 새로운 구간을 추가
    - [x] 기존 구간 A-C에 신규 구간 A-B를 추가하는 경우 A역을 기준으로 추가
        - [x] 상행, 하행 기준으로 추가
    - [x] 기존 구간과 신규 구간이 모두 같을 순 없음
    - [x] 결과로 A-B, B-C 구간이 생김
        - [x] 상행, 하행 기준으로 추가
- [x] 새로운 길이를 뺀 나머지를 새롭게 추가된 역과의 길이로 설정
    - [x] 역 사이에 새로운 역을 등록할 경우 기존 역 사이 길이보다 크거나 같으면 등록을 할 수 없음
- [x] 상행역과 하행역 둘 중 하나도 포함되어있지 않으면 추가할 수 없음
- [x] 아래의 순서대로 역 목록을 응답하는 로직을 변경해야 함
    1. 상행 종점이 상행역인 구간을 먼저 찾는다.
    2. 그 다음, 해당 구간의 하행역이 상행역인 다른 구간을 찾는다.
    3. 2번을 반복하다가 하행 종점역을 찾으면 조회를 멈춘다.

----

## 3단계 - 기능

- [x] 기존에는 마지막 역 삭제만 가능했는데 위치에 상관 없이 삭제가 가능하도록 수정
- [x] 종점이 제거될 경우 다음으로 오던 역이 종점이 됨
- [x] 중간역이 제거될 경우 재배치를 함
    1. 노선에 A - B - C 역이 연결되어 있을 때 B역을 제거할 경우 A - C로 재배치 됨
    2. 거리는 두 구간의 거리의 합으로 정함
