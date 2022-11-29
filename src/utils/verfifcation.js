export function CKUsername(data){
    // let reg = /^([a-zA-Z][0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
    let reg = /^(?!\D+$)(?![^a-a-zA-Z]+$)\S{6,15}$/;
    return !reg.test(data)?true:false
}
export function CKPass(data){
    let reg = /^(?!\D+$)(?![^a-a-zA-Z]+$)\S{6,15}$/;
    return !reg.test(data)?true:false
}